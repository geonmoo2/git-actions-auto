document.addEventListener("DOMContentLoaded", function() {
    loadStories();

    document.getElementById("storyForm").addEventListener("submit", function(event) {
        event.preventDefault();
        submitForm();
    });
});

function loadStories() {
    fetch("/fan/stories")
        .then(response => response.json())
        .then(data => {
            const storyList = document.getElementById("story-list");
            storyList.innerHTML = "";
            data.forEach(story => {
                storyList.innerHTML += `
                    <div class="story">
                        <div class="story-image"><img src="/images/${story.image}" alt="Story Image"></div>
                        <div class="story-content">
                            <h2>${story.title}</h2>
                            <p>${story.content}</p>
                            <div class="story-footer">
                                <span>Author: ${story.author}</span>
                                <span>Views: ${story.views}</span>
                                <span class="edit" onclick="editStory(${story.id})">Edit</span>
                                <span class="delete" onclick="deleteStory(${story.id})">Delete</span>
                            </div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(error => displayError("Failed to load stories. " + error.message));
}

function showForm() {
    document.getElementById("story-form").style.display = "block";
    document.getElementById("form-title").innerText = "Add Story";
    document.getElementById("storyForm").reset();
}

function hideForm() {
    document.getElementById("story-form").style.display = "none";
}

function submitForm() {
    const form = document.getElementById("storyForm");
    const formData = new FormData(form);
    const storyId = document.getElementById("storyId").value;

    const url = storyId ? `/fan/story/${storyId}` : "/fan/story";
    const method = storyId ? "PUT" : "POST";

    fetch(url, {
        method: method,
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message); });
            }
            return response.json();
        })
        .then(data => {
            hideForm();
            loadStories();
        })
        .catch(error => displayError("Failed to submit story. " + error.message));
}

function editStory(id) {
    fetch(`/fan/story/${id}`)
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message); });
            }
            return response.json();
        })
        .then(story => {
            document.getElementById("storyId").value = story.id;
            document.getElementById("title").value = story.title;
            document.getElementById("author").value = story.author;
            document.getElementById("content").value = story.content;
            document.getElementById("form-title").innerText = "Edit Story";
            document.getElementById("story-form").style.display = "block";
        })
        .catch(error => displayError("Failed to load story for editing. " + error.message));
}

function deleteStory(id) {
    fetch(`/fan/story/${id}`, {
        method: "DELETE"
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw new Error(err.message); });
            }
            loadStories();
        })
        .catch(error => displayError("Failed to delete story. " + error.message));
}

function displayError(message) {
    const errorMessage = document.getElementById("error-message");
    errorMessage.style.display = "block";
    errorMessage.innerText = message;
}
