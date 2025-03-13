export const addPostsToDOM = (container, posts, clickHandler) => {
    posts.forEach(post => {
        let postElement = document.createElement('div');
        postElement.className = 'card';
        postElement.id = post.id;
        postElement.style.cursor = 'pointer';
        postElement.innerHTML = `
            <h3>${post.subject}</h3>
            <p>${post.body}</p>
            <h4>Author: ${post.author.name} (${post.author.nickName})</h4>
            <small>Tags: ${post.tags.join(', ')}</small>
            <small>Created: ${post.createdDate}</small>
        `;
        postElement.addEventListener('click', () => clickHandler(post.id));
        container.appendChild(postElement);
    });
};

export const showPostDetails = (container, post) => {
    let postElement = `
        <div class="post">
            <h3>${post.subject}</h3>
            <p>${post.body}</p>
            <h4>Author: ${post.author.name} (${post.author.nickName})</h4>
            <small>Tags: ${post.tags.join(', ')}</small>
            <small>Created: ${post.createdDate}</small>
            <button id="backButton">Back to list</button>
        </div>
    `;
    container.innerHTML = postElement;
};

export const addUsersToDOM = (container, users, clickHandler) => {
    users.forEach(user => {
        let userElement = document.createElement('div');
        userElement.className = 'card';
        userElement.id = user.id;
        userElement.style.cursor = 'pointer';
        userElement.innerHTML = `
            <h3>${user.name} (${user.nickName})</h3>
            <small>Created: ${user.createdDate}</small>
            <small>Last Modified: ${user.lastModifiedDate}</small>
        `;
        userElement.addEventListener('click', () => clickHandler(user.id));
        container.appendChild(userElement);
    });
};

export const showUserDetails = (container, user) => {
    let userElement = `
        <div class="user">
            <h3>${user.name} (${user.nickName})</h3>
            <small>Created: ${user.createdDate}</small>
            <small>Last Modified: ${user.lastModifiedDate}</small>
            <button id="backButton">Back to list</button>
        </div>
    `;
    container.innerHTML = userElement;
};

export const clearContainer = (container) => {
    container.innerHTML = '';
};


export const hideElement = (element) => {
    element.style.display = 'none';
};

export const showElement = (element) => {
    element.style.display = 'block';
};