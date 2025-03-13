class FooterComponent extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
            <footer>
                <div class="footer-inner-container">
                    <p>Copyright &copy; 2025. All rights reserved.</p>
                </div>
            </footer>`;
    }
}
customElements.define('footer-component', FooterComponent);