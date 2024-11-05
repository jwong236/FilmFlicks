import { useState, useEffect } from 'react';
import axios from 'axios';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useGenres = (navigate) => {
    const [genres, setGenres] = useState(["loading..."]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchGenres = async () => {
            try {

                const response = await axios.get(`${URL}/metadata/genres`, {
                    withCredentials: true
                });
                console.log(response)
                const tempArray = response.data.map(elem => elem.name);
                setGenres(tempArray);
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    console.log("Unauthorized access: Redirecting to login page");
                    navigate('/auth/login'); // Redirect on specific status code (401)
                } else {
                    console.error("Error fetching genres:", error);
                    setError(error);
                }
            }
        };
        fetchGenres();
    }, [navigate]);

    return { genres, error };
};