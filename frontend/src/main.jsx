/**
 * Entry point for the React application.
 * Sets up the root React component and mounts it to the DOM.
 */
import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';


const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <App /> {}
        </BrowserRouter>
    </React.StrictMode>
);
