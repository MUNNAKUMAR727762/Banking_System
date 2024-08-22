// Function to load content from the specified URL
function loadContent(url) {
    console.log("Loading content from:", url);
    fetch(url)
        .then(response => {
            console.log("Response status:", response.status);
            return response.text();
        })
        .then(data => {
            console.log("Data received:", data);
            const loadedContentDiv = document.createElement('div');
            loadedContentDiv.classList.add('loaded-content');
            loadedContentDiv.innerHTML = data;

            document.getElementById('content-placeholder').innerHTML = '';
            document.getElementById('content-placeholder').appendChild(loadedContentDiv);

            // After loading content, populate personal information if available
            // If there is a specific function to handle this, you can call it here.
            // For now, the function call is removed since it's causing issues.
        })
        .catch(error => console.error('Error loading content:', error));
}

function toggleSidebar() {
    document.getElementById('sidebar').classList.toggle('hidden');
}

// Trigger content load on page load
window.onload = function() {
    loadContent('PersonalInformation.jsp');
};
