const socket = new SockJS('/ws');
const stompClient = Stomp.over(socket);
let username;

stompClient.connect({}, function (frame) {
  console.log('Connected: ' + frame);

  // Subscribe to the user's message queue
  username = prompt('Enter your username:'); // Prompt for username
  stompClient.subscribe(`/user/${username}/queue/messages`, function (message) {
    const msg = JSON.parse(message.body);
    displayMessage(msg.sender + ': ' + msg.content);
  });
});

document.getElementById('sendBtn').addEventListener('click', function () {
  const recipient = document.getElementById('recipient').value;
  const message = document.getElementById('message').value;

  // Send message to the server
  stompClient.send(
    '/app/chat',
    {},
    JSON.stringify({
      sender: username,
      receiver: recipient,
      content: message,
    })
  );

  // Clear the input field
  document.getElementById('message').value = '';
});

// Function to display received messages
function displayMessage(message) {
  const messagesDiv = document.getElementById('messages');
  const newMessage = document.createElement('div');
  newMessage.classList.add('message');
  newMessage.textContent = message;
  messagesDiv.appendChild(newMessage);
  messagesDiv.scrollTop = messagesDiv.scrollHeight; // Auto-scroll to the latest message
}
