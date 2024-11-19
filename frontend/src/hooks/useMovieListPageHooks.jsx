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
            const URL = import.meta.env.VITE_BACKEND_URL;

            let endpoint = `${URL}`;
            let params = { page: pageData.pageNumber, pageSize: pageData.pageSize, sortRule: pageData.sortRule };

            // Check if character, genre, or title exists in locationState
            if (locationState?.character) {
                endpoint += `/browse/character`;
                params = { character: locationState.character, ...params };
            } else if (locationState?.genre) {
                endpoint += `/browse/genre`;
                params = { genre: locationState.genre, ...params };
            } else if (locationState?.title) {
                endpoint += `/search`;
                params = { title: locationState.title, ...params };
            } else {
                // Fetch previous request if no valid params exist
                const previousRequestResponse = await axios.get(`${URL}/session/stored-request/get`, {
                    withCredentials: true
                });

                if (previousRequestResponse.status !== 200 || !previousRequestResponse.data) {
                    throw new Error("No previous request found in session.");
                }

                const { endpoint: storedEndpoint, params: storedParams } = previousRequestResponse.data;

                if (!storedEndpoint || !storedParams) {
                    throw new Error("No valid previous request found in session.");
                }

                endpoint = storedEndpoint;
                params = storedParams;
            }

            // Add the request to the session
            await axios.post(
                `${URL}/session/stored-request/add`,
                null,
                {
                    params: { endpoint, ...params },
                    withCredentials: true
                }
            );

            // Get session contents for debugging
            const sessionContents = await axios.get(`${URL}/session/`, {
                withCredentials: true
            });

            // Fetch the movies using the determined endpoint and params
            const response = await axios.get(endpoint, {
                params,
                withCredentials: true
            });

            // Format and set the movies in the state
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
                setSnackbar({ open: true, message: "Failed to load movies. Please try again later." });
            }
        }
    };




    const addToShoppingCart = async (movie) => {
        try {
            // Use `id` (string) and other details
            const response = await fetch(`${URL}/transaction/shopping-cart/add?id=${encodeURIComponent(movie.id)}&title=${encodeURIComponent(movie.title)}&price=${movie.price ?? 10}&quantity=1`, {
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
    };

    const closeSnackbar = () => {
        setSnackbar({ open: false, message: "" });
    };

    const setPageSize = (size) => {
        setPageData(prev => ({ ...prev, pageSize: size }));
    };

    const setSortRule = (rule) => {
        setPageData(prev => ({ ...prev, sortRule: rule }));
    };

    const handleNextClick = () => {
        setPageData(prev => ({ ...prev, pageNumber: prev.pageNumber + 1 }));
    };

    const handlePrevClick = () => {
        setPageData(prev => ({ ...prev, pageNumber: prev.pageNumber > 1 ? prev.pageNumber - 1 : 1 }));
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
