import { createPost } from './api.js'; 

document.getElementById('postForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const authorId = document.getElementById('authorId').value;
    const subject = document.getElementById('subject').value;
    const body = document.getElementById('body').value;
    const tagsInput = document.getElementById('tags').value;
    const tags = tagsInput ? tagsInput.split(',').map(tag => tag.trim()) : [];

    const postData = {
        author: { id: authorId },
        body: body,
        subject: subject,
        tags: tags
    };

    createPost(postData)
    .then(data => {
        console.log('Post created successfully:', data);
        alert('Post created successfully!');
    })
    .catch(error => {
        console.error('Failed to create post:', error);
        alert('Failed to create post.');
    });
});