import { fetchPosts, fetchPostDetails } from './api.js';
import { clearContainer, addPostsToDOM, showPostDetails, hideElement, showElement } from './ui.js';

let page = 0;
let totalPages = 1;
let isFetching = false;

window.onload = function() {
    const postsContainer = document.getElementById('postsContainer');
    const postDetailsContainer = document.getElementById('postDetailsContainer');

    function getPosts() {
        if (page >= totalPages || isFetching) {
            return;
        }
        isFetching = true;

        fetchPosts(page, 5)
            .then(data => {
                totalPages = data.totalPages;
                if (page === 0) {
                    clearContainer(postsContainer);
                }
                addPostsToDOM(postsContainer, data.content, getPostDetails);
                page++;
                isFetching = false;
            })
            .catch(error => {
                console.error('Error fetching posts:', error);
                postsContainer.innerHTML = '<p>Error loading posts. Please try again later.</p>';
                isFetching = false;
            });
    }

    window.addEventListener('scroll', () => {
        const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 100;
        if (nearBottom && !isFetching) {
            console.log('Near bottom, triggering getPosts');
            getPosts();
        }
    });

    function getPostDetails(postId) {
        hideElement(postsContainer);

        fetchPostDetails(postId)
            .then(data => {
                showPostDetails(postDetailsContainer, data);
                document.getElementById('backButton').addEventListener('click', () => {
                    postDetailsContainer.innerHTML = '';
                    showElement(postsContainer);
                });
            });
    }

    getPosts();
};