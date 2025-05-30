document.addEventListener('DOMContentLoaded', function() {
  // Elements
  const calculatorForm = document.getElementById('calculatorForm');
  const resultContainer = document.getElementById('resultContainer');
  const resultDisplay = document.getElementById('resultDisplay');
  const historyList = document.getElementById('historyList');
  const firstOperandInput = document.getElementById('firstOperand');
  const secondOperandInput = document.getElementById('secondOperand');
  
  // Validação para garantir que apenas números sejam aceitos
  firstOperandInput.addEventListener('input', validateNumberInput);
  secondOperandInput.addEventListener('input', validateNumberInput);
  
  // Load calculation history when page loads
  loadHistory();
  
  // Função para validar entrada de números
  function validateNumberInput(e) {
    const input = e.target;
    const value = input.value;
    
    // Permite apenas dígitos e um único ponto decimal
    // Remove qualquer caractere que não seja dígito ou ponto
    const sanitizedValue = value.replace(/[^0-9.]/g, '');
    
    // Garante que haja apenas um ponto decimal
    const parts = sanitizedValue.split('.');
    if (parts.length > 2) {
      // Se houver mais de um ponto, mantém apenas o primeiro
      input.value = parts[0] + '.' + parts.slice(1).join('');
    } else {
      input.value = sanitizedValue;
    }
  }
  
  // Handle form submission
  calculatorForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Get form values
    const firstOperand = document.getElementById('firstOperand').value;
    const secondOperand = document.getElementById('secondOperand').value;
    const operator = document.getElementById('operator').value;
    
    // Validate inputs
    if (!firstOperand || !secondOperand) {
      showError('Por favor, preencha todos os campos.');
      return;
    }
    
    // Check for division by zero
    if (operator === '/' && parseFloat(secondOperand) === 0) {
      showError('Não é possível dividir por zero.');
      return;
    }
    
    // Send calculation request
    calculate(firstOperand, secondOperand, operator);
  });
  
  // Function to perform calculation
  function calculate(firstOperand, secondOperand, operator) {
    fetch('/calculate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        firstOperand,
        secondOperand,
        operator
      })
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Erro ao realizar cálculo');
      }
      return response.json();
    })
    .then(data => {
      // Display result
      resultContainer.style.display = 'block';
      // Formatar o resultado com vírgula como separador decimal e 2 casas decimais
      const formattedResult = Number(data.result).toLocaleString('pt-BR', {
        minimumFractionDigits: 2,
        maximumFractionDigits: 2
      });
      resultDisplay.textContent = formattedResult;
      
      // Limpar os campos de entrada para a próxima operação
      document.getElementById('firstOperand').value = '';
      document.getElementById('secondOperand').value = '';
      
      // Reload history
      loadHistory();
    })
    .catch(error => {
      showError(error.message);
    });
  }
  
  // Function to load calculation history
  function loadHistory() {
    fetch('/history')
      .then(response => {
        if (!response.ok) {
          throw new Error('Erro ao carregar histórico');
        }
        return response.json();
      })
      .then(data => {
        // Clear history list
        historyList.innerHTML = '';
        
        if (data.length === 0) {
          historyList.innerHTML = '<p class="no-history-message">Nenhum cálculo realizado ainda.</p>';
          return;
        }
        
        // Sort history by timestamp (newest first)
        data.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));
        
        // Add each history item
        data.forEach(item => {
          const historyItem = document.createElement('div');
          historyItem.className = 'history-item';
          
          const operation = document.createElement('div');
          operation.className = 'history-operation';
          // Formatar os operandos e o resultado com vírgula como separador decimal
          const formattedFirstOperand = Number(item.firstOperand).toLocaleString('pt-BR', {
            minimumFractionDigits: 1,
            maximumFractionDigits: 2
          });
          
          const formattedSecondOperand = Number(item.secondOperand).toLocaleString('pt-BR', {
            minimumFractionDigits: 1,
            maximumFractionDigits: 2
          });
          
          // Formatar o resultado com vírgula como separador decimal e 2 casas decimais
          const formattedResult = Number(item.result).toLocaleString('pt-BR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
          });
          
          // Construir a operação formatada
          const formattedOperation = `${formattedFirstOperand} ${item.operator} ${formattedSecondOperand}`;
          
          operation.textContent = `${formattedOperation} = ${formattedResult}`;
          
          const timestamp = document.createElement('div');
          timestamp.className = 'history-timestamp';
          timestamp.textContent = formatDate(new Date(item.timestamp));
          
          historyItem.appendChild(operation);
          historyItem.appendChild(timestamp);
          historyList.appendChild(historyItem);
        });
      })
      .catch(error => {
        historyList.innerHTML = `<p class="error-message">${error.message}</p>`;
      });
  }
  
  // Helper function to format date
  function formatDate(date) {
    return new Intl.DateTimeFormat('pt-BR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
      second: '2-digit'
    }).format(date);
  }
  
  // Function to show error message
  function showError(message) {
    resultContainer.style.display = 'block';
    resultDisplay.innerHTML = `<span style="color: #721c24;">${message}</span>`;
  }
});