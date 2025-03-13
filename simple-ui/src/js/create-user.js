import { createUser } from './api.js'; 

document.getElementById('userForm').addEventListener('submit', function(event) {
    event.preventDefault(); 

    const name = document.getElementById('name').value;
    const nickName = document.getElementById('nickName').value;

    const userData = {
        name: name,
        nickName: nickName
    };

    createUser(userData)
    .then(data => {
        console.log('Success:', data);
        alert('User created successfully!');
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('Failed to create user.');
    });
});