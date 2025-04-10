class HeaderComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
            <header>
                <div class="header-inner-container">
                    <div id="logo">The Lord of the Rings Blog</div>
                    <nav>
                        <a href="index.html">Home</a>
                        <a href="users.html">Users</a>
                        <a href="tags.html">Tags</a>
                        <a href="create-user.html">Create User</a>
                        <a href="create-post.html">Create Post</a>
                        <a href="about.html">About</a>
                        <a href="#">Contact</a>
                    </nav>
                </div>
            </header>`;
    }
}
customElements.define('header-component', HeaderComponent);