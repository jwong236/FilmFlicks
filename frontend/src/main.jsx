import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import App from './App.jsx';
import MovieList from '../src/pages/MovieList.jsx';
import SingleMovie from '../src/pages/SingleMovie.jsx';
import SingleStar from '../src/pages/SingleStar.jsx';
import Example from '../src/pages/Example.jsx';
import './index.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<MovieList />} />
                <Route path="/movielist" element={<MovieList />} />
                <Route path="/singlemovie" element={<SingleMovie />} />
                <Route path="/singlestar" element={<SingleStar />} />
                <Route path="/example" element={<Example />} />
            </Routes>
        </BrowserRouter>
    </React.StrictMode>
);
