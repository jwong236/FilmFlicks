
// useMovieListPageHooks.js (in hooks directory)
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export const useMovieListPageHooks = () => {
    const [pageData, setPageData] = useState({
        pageNumber: 1,
        pageSize: 10,
        sortRule: "title_asc_rating_asc",
        movies: []
    });
    const [snackbar, setSnackbar] = useState({ open: false, message: "" });
    const navigate = useNavigate();
    const URL = import.meta.env.VITE_BACKEND_URL;

    const fetchMovies = async (locationState) => {
        try {
            let endpoint = `${URL}`;
            let params = { page: pageData.pageNumber, pageSize: pageData.pageSize, sortRule: pageData.sortRule };

            if (locationState?.character) {
                endpoint += `/browse/character`;
                params = { character: locationState.character, ...params };
            } else if (locationState?.genre) {
                endpoint += `/browse/genre`;
                params = { genre: locationState.genre, ...params };
            } else if (locationState?.title) {
                endpoint += `/search`;
                params = { title: locationState.title, ...params };
            }

            const response = await axios.get(endpoint, {
                params,
                withCredentials: true
            });

            const formattedMovies = response.data.map(movie => ({
                id: movie.id,
                title: movie.title,
                year: movie.year,
                director: movie.director,
                stars: movie.stars.map(star => ({ id: star.id, name: star.name, birthYear: star.birth_year })),
                genres: movie.genres.map(genre => ({ id: genre.id, name: genre.name })),
                rating: movie.rating ? movie.rating.rating : null,
                numVotes: movie.rating ? movie.rating.numVotes : null
            }));

            setPageData(prev => ({ ...prev, movies: formattedMovies }));
        } catch (error) {
            if (error.response && error.response.status === 401) {
                navigate('/login');
            } else {
                console.error("Error fetching movie list:", error);
            }
        }
    };

    const handleNextClick = () => {
        setPageData(prev => ({ ...prev, pageNumber: prev.pageNumber + 1 }));
    };

    const handlePrevClick = () => {
        setPageData(prev => ({ ...prev, pageNumber: prev.pageNumber > 1 ? prev.pageNumber - 1 : 1 }));
    };

    const addToShoppingCart = async (movie) => {
        try {
            const response = await fetch(`${URL}/transaction/shopping-cart/add?title=${encodeURIComponent(movie.title)}&price=${movie.price ?? 10}&quantity=1`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include'
            });

            if (response.status === 401) {
                navigate('/login');
            } else {
                setSnackbar({ open: true, message: "Movie added successfully!" });
            }
        } catch (error) {
            console.error('Error adding to cart: ', error);
            setSnackbar({ open: true, message: "Failed to add movie to cart." });
        }
    }; // IMPORTANT: price is arbitrarily set to 10 in this hook! All movies will be priced at 10 until I populate the database with movie prices

    const closeSnackbar = () => {
        setSnackbar({ open: false, message: "" });
    };

    const setPageSize = (size) => {
        setPageData(prev => ({ ...prev, pageSize: size }));
    };

    const setSortRule = (rule) => {
        setPageData(prev => ({ ...prev, sortRule: rule }));
    };

    return {
        pageData,
        snackbar,
        fetchMovies,
        handleNextClick,
        handlePrevClick,
        addToShoppingCart,
        setPageSize,
        setSortRule,
        closeSnackbar,
    };
};
