export const fetchPosts = (page, size) => {
    return fetch(`http://localhost:8090/blog/posts?page=${page}&size=${size}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
};

export const fetchPostDetails = (postId) => {
    return fetch(`http://localhost:8090/blog/posts/${postId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
};

export const fetchUsers = (page, size) => {
    return fetch(`http://localhost:8090/blog/users?page=${page}&size=${size}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
};

export const fetchUserDetails = (userId) => {
    return fetch(`http://localhost:8090/blog/users/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        });
};

export const createUser = (userData) => {
    return fetch('http://localhost:8090/blog/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    });
};

export const createPost = (postData) => {
    return fetch('http://localhost:8090/blog/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    });
};
