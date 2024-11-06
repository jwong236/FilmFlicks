import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useTopMoviesPageHooks = () => {
    const [movieData, setMovieData] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        const fetchMovieData = async () => {
            try {
                const response = await axios.get(`${URL}/metadata/top-rated?size=20`, {
                    withCredentials: true
                });
                if (mounted && response.status === 200) {
                    setMovieData(response.data);
                }
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    navigate('/login');
                } else {
                    console.error('Error fetching data: ', error);
                }
            }
        };

        fetchMovieData();
        return () => {
            mounted = false;
        };
    }, [navigate]);

    return { movieData, navigate };
};
