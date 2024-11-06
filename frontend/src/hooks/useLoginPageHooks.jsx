import axios from 'axios';
import { useState } from "react";
import { useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useLoginPageHooks = () => {
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [result, setResult] = useState('');

    const login = async () => {
        try {
            const loginData = new URLSearchParams();
            loginData.append('username', email);
            loginData.append('password', password);

            const response = await axios.post(`${URL}/login`, loginData, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                withCredentials: true
            });

            if (response.status === 200 && response.data.status === "success") {
                setResult('Login Successful!');
                navigate('/homepage');
            }
        } catch (error) {
            console.error("Login failed:", error);
            setResult('Login failed');
        }
    };

    return {
        email, setEmail,
        password, setPassword,
        result, setResult,
        login
    };
};
