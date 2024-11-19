import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_BACKEND_URL;

export function useSingleStarPageHooks() {
    const [starData, setStarData] = useState({
        name: '',
        id: '',
        birth_year: '',
        movies: [],
    });
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        const searchParams = new URLSearchParams(location.search);
        const name = searchParams.get('name');

        async function fetchStarData() {
            try {
                const response = await fetch(`${URL}/single-star?name=${encodeURIComponent(name)}`, {
                    credentials: 'include',
                });

                if (response.status === 401) {
                    console.log("Redirecting to login...");
                    navigate('/login');
                } else if (response.ok && mounted) {
                    const jsonData = await response.json();
                    setStarData(jsonData);
                } else {
                    console.error('Failed to fetch star data');
                }
            } catch (error) {
                console.error('Error fetching data: ', error);
            }
        }

        if (name) {
            fetchStarData();
        }

        return () => {
            mounted = false;
        };
    }, [location, navigate]);

    return { starData };
}
