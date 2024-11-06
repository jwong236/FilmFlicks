import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from "react-router-dom";

const URL = import.meta.env.VITE_BACKEND_URL;

export const useSingleMoviePageHooks = () => {
    const [movieData, setMovieData] = useState({
        title: "default title",
        year: "default year",
        director: "default director",
        genres: ["genre1"],
        stars: ["star1"],
        rating: "N/A",
    });

    const location = useLocation();
    const search_params = new URLSearchParams(location.search);
    const title = search_params.get('title');
    const navigate = useNavigate();
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState("");

    useEffect(() => {
        let mounted = true;

        async function fetchMovieData() {
            try {
                const response = await fetch(`${URL}/single-movie?title=${encodeURIComponent(title)}`, {
                    credentials: 'include',
                });

                if (response.status === 401) {
                    console.log("Redirecting to login...");
                    navigate('/login');
                } else if (response.ok) {
                    const jsonData = await response.json();
                    if (mounted && jsonData) {
                        // Map the response to match the state structure
                        setMovieData({
                            title: jsonData.title,
                            year: jsonData.year,
                            director: jsonData.director,
                            genres: jsonData.genres.map((genre) => genre.name), // Extract genre names
                            stars: jsonData.stars.map((star) => star.name),   // Extract star names
                            rating: jsonData.rating ? jsonData.rating.rating : "N/A", // Extract rating if available
                        });
                    }
                } else {
                    console.error('Error: response is not status 200');
                }
            } catch (error) {
                console.error('Error fetching data: ', error);
            }
        }

        fetchMovieData();

        return () => {
            mounted = false;
        };
    }, [title, navigate]);

    const addToShoppingCart = async (movie) => {
        try {
            const response = await fetch(`${URL}/add`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify({ "movieTitle": movie.title }),
            });

            if (response.status === 401) {
                navigate('/login');
            } else if (response.ok) {
                setSnackbarMessage("Movie added successfully!");
                setOpenSnackbar(true);
            } else {
                throw new Error('Failed to add movie');
            }
        } catch (error) {
            console.error('Error adding to cart: ', error);
            setSnackbarMessage("Failed to add movie to cart.");
            setOpenSnackbar(true);
        } finally {
            setTimeout(() => {
                setOpenSnackbar(false);
            }, 3000);
        }
    };

    return {
        movieData,
        addToShoppingCart,
        openSnackbar,
        snackbarMessage,
        setOpenSnackbar,
    };
};
