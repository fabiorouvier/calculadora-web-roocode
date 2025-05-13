const express = require('express');
const session = require('express-session');
const cookieParser = require('cookie-parser');
const axios = require('axios');
const path = require('path');

const app = express();
const PORT = 3000;
const BACKEND_URL = 'http://localhost:8081';

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(session({
  secret: 'calculadora-web-secret',
  resave: false,
  saveUninitialized: false,
  cookie: { secure: false } // Set to true if using HTTPS
}));

// Set view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Serve static files
app.use(express.static(path.join(__dirname, 'public')));

// Authentication middleware
const isAuthenticated = (req, res, next) => {
  if (req.session.user) {
    return next();
  }
  res.redirect('/login');
};

// Routes
app.get('/', (req, res) => {
  if (req.session.user) {
    res.redirect('/calculator');
  } else {
    res.redirect('/login');
  }
});

// Login page
app.get('/login', (req, res) => {
  res.render('login', { error: null });
});

// Register page
app.get('/register', (req, res) => {
  res.render('register', { error: null });
});

// Login handler
app.post('/login', async (req, res) => {
  const { username, password } = req.body;
  
  try {
    // Create form data for Spring Security form login
    const params = new URLSearchParams();
    params.append('username', username);
    params.append('password', password);
    
    const response = await axios.post(`${BACKEND_URL}/api/login`, params, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      withCredentials: true,
      maxRedirects: 0,
      validateStatus: status => status >= 200 && status < 300
    });
    
    // If we get here, login was successful
    req.session.user = { username };
    
    // Store the JSESSIONID cookie from the backend
    if (response.headers['set-cookie']) {
      const cookies = response.headers['set-cookie'];
      req.session.backendCookies = cookies;
    }
    
    res.redirect('/calculator');
  } catch (error) {
    console.error('Login error:', error.message);
    res.render('login', { error: 'Usuário ou senha inválidos' });
  }
});

// Register handler
app.post('/register', async (req, res) => {
  const { username, password } = req.body;
  
  try {
    await axios.post(`${BACKEND_URL}/api/users/register`, {
      username,
      password
    });
    
    res.redirect('/login');
  } catch (error) {
    console.error('Registration error:', error.message);
    res.render('register', { error: 'Registration failed. Username may already exist.' });
  }
});

// Calculator page (protected)
app.get('/calculator', isAuthenticated, (req, res) => {
  res.render('calculator', { user: req.session.user });
});

// Calculate endpoint
app.post('/calculate', isAuthenticated, async (req, res) => {
  const { firstOperand, secondOperand, operator } = req.body;
  
  try {
    const response = await axios.post(`${BACKEND_URL}/api/calculator/calculate`, {
      firstOperand: parseFloat(firstOperand),
      secondOperand: parseFloat(secondOperand),
      operator
    }, {
      headers: {
        Cookie: req.session.backendCookies?.join('; ')
      }
    });
    
    res.json(response.data);
  } catch (error) {
    console.error('Calculation error:', error.message);
    res.status(400).json({ error: 'Calculation failed' });
  }
});

// History endpoint
app.get('/history', isAuthenticated, async (req, res) => {
  try {
    const response = await axios.get(`${BACKEND_URL}/api/calculator/history`, {
      headers: {
        Cookie: req.session.backendCookies?.join('; ')
      }
    });
    
    res.json(response.data);
  } catch (error) {
    console.error('History fetch error:', error.message);
    res.status(400).json({ error: 'Failed to fetch history' });
  }
});

// Logout
app.get('/logout', async (req, res) => {
  try {
    // Call backend logout
    await axios.post(`${BACKEND_URL}/api/logout`, {}, {
      headers: {
        Cookie: req.session.backendCookies?.join('; ')
      }
    });
  } catch (error) {
    console.error('Logout error:', error.message);
  }
  
  // Clear session
  req.session.destroy();
  res.redirect('/login');
});

// Start server
app.listen(PORT, () => {
  console.log(`Frontend server running at http://localhost:${PORT}`);
});