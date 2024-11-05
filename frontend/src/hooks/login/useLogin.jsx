import axios from 'axios';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useLogin = (setResult, navigate) => {
    const login = async (email, password) => {
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

    return { login };
};
