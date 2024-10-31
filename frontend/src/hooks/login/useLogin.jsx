// src/hooks/login/useLogin.js
import { useNavigate } from "react-router-dom";

export const useLogin = (setResult) => {
    const navigate = useNavigate();

    const login = async (email, password) => {
        try {
            // Prepare login data in x-www-form-urlencoded format
            const loginData = new URLSearchParams();
            loginData.append('username', email);
            loginData.append('password', password);

            // Use environment variable for backend URL with a fallback
            const URL = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';
            console.log("Backend URL:", URL);  // Debugging to check the URL

            // Send the login request to the server using x-www-form-urlencoded
            const response = await fetch(`${URL}/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: loginData.toString(),
            });
            console.log( "test", response)

            // Handle the response
            if (response.status === 200) {
                setResult('Login Successful!');
                navigate('/homepage');
            } else if (response.status === 401) {
                setResult('Incorrect Password!');
            } else if (response.status === 406) {
                setResult('Account Does Not Exist!');
            } else if (response.status === 404) {
                setResult('Not Found! Check if the URL is correct.');
            } else {
                setResult('Login Failed!');
            }
        } catch (error) {
            console.error('Login error:', error);
            setResult('An error occurred. Please try again.');
        }
    };

    return { login };
};
