import { fetchTags, fetchTagDetails } from './api.js';
import { addTagsToDOM, showTagDetails, clearContainer, hideElement, showElement } from './ui.js';

let page = 0;
let totalPages = 1;
let isFetching = false;

window.onload = function() {
    const tagsContainer = document.getElementById('tagsContainer');
    const tagDetailsContainer = document.getElementById('tagDetailsContainer');

    function getTags() {
        if (page >= totalPages || isFetching) {
            return;
        }
        isFetching = true;

        fetchTags(page, 5)
            .then(data => {
                totalPages = data.totalPages;
                if (page === 0) {
                    clearContainer(tagsContainer);
                }
                addTagsToDOM(tagsContainer, data.content, getTagDetails);
                page++;
                isFetching = false;
            })
            .catch(error => {
                console.error('Error fetching tags:', error);
                tagsContainer.innerHTML = '<p>Error loading tags. Please try again later.</p>';
                isFetching = false;
            });
    }

    window.addEventListener('scroll', () => {
        const nearBottom = window.innerHeight + window.scrollY >= document.body.offsetHeight - 100;
        if (nearBottom && !isFetching) {
            console.log('Near bottom, triggering getTags');
            getTags();
        }
    });

    function getTagDetails(tagId) {
        hideElement(tagsContainer);

        fetchTagDetails(tagId)
            .then(data => {
                showTagDetails(tagDetailsContainer, data);
                document.getElementById('backButton').addEventListener('click', () => {
                    tagDetailsContainer.innerHTML = '';
                    showElement(tagsContainer);
                });
            });
    }

    getTags();
};