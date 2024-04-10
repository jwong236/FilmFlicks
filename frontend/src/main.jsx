import React from 'react'
import ReactDOM from 'react-dom/client';
import App from './App.jsx'
import MovieList from '../src/pages/MovieList.jsx'
import SingleStar from '../src/pages/SingleStar.jsx'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    {/*<App />*/}
      <BrowserRouter>
          <Routes>
              <Route path="/" element={<MovieList/>} />
              <Route path="/singlestar" element={<SingleStar/>} />

          </Routes>

      </BrowserRouter>
  </React.StrictMode>,
)
