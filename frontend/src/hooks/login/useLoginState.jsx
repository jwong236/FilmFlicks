// src/hooks/useLoginState.js
import { useState } from 'react';

export const useLoginState = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [result, setResult] = useState('');

    return { email, setEmail, password, setPassword, result, setResult };
};
