import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useHomePageHooks = () => {
    const navigate = useNavigate();
    const [genres, setGenres] = useState([]);

    useEffect(() => {
        const fetchGenres = async () => {
            try {
                const response = await axios.get(`${URL}/metadata/genres`, {
                    withCredentials: true,
                });
                setGenres(response.data.map((genre) => genre.name));
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    navigate('/login');
                } else {
                    console.error("Error fetching genres:", error);
                }
            }
        };
        fetchGenres();
    }, [navigate]);

    const handleNavigate = (type, value) => {
        navigate('/movielist', { state: { [type]: value } });
    };

    return { genres, handleNavigate };
};
