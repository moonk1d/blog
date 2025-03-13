import { fetchUsers, fetchUserDetails } from './api.js';
import { addUsersToDOM, showUserDetails, clearContainer, hideElement, showElement } from './ui.js';

let page = 0;
let totalPages = 1;
let isFetching = false;

window.onload = function() {

    const usersContainer = document.getElementById('usersContainer');
    const userDetailsContainer = document.getElementById('userDetailsContainer');
    
    function getUsers() {
        if (page >= totalPages || isFetching) {
            return;
        }
        isFetching = true;

            fetchUsers(page, 5)
                .then(data => {
                    totalPages = data.totalPages;
                    if (page === 0) {
                        clearContainer(usersContainer);
                    }
                    addUsersToDOM(usersContainer, data.content, getUserDetails);
                    page++;
                    isFetching = false;
                })
                .catch(error => {
                    console.error('Error fetching users:', error);
                    usersContainer.innerHTML = '<p>Error loading users. Please try again later.</p>';
                });
    }

    window.addEventListener('scroll', () => {
        const nearBottom = window.innerHeight + window.scrollY <= document.body.offsetHeight + 10000;
        if (nearBottom && !isFetching) {
            console.log('Near bottom, triggering getUsers');
            getUsers();
        }
    });

    function getUserDetails(userId) {
        hideElement(usersContainer);

        fetchUserDetails(userId)
        .then(data => {
            console.log(userDetailsContainer)
            showUserDetails(userDetailsContainer, data);
            document.getElementById('backButton').addEventListener('click', () => {
                userDetailsContainer.innerHTML = '';
                showElement(usersContainer);
            });
        });
    }

    getUsers();

};