import React, { useState } from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import ShoppingCartCard from '../components/ShoppingCartCard.jsx';
import { useNavigate } from 'react-router-dom';

export default function ShoppingCart() {
    const [cartData, setCartData] = useState([
        { title: "Inception", quantity: 1, price: 10.00, total: 10.00 },
        { title: "The Matrix", quantity: 2, price: 15.00, total: 30.00 }
    ]);
    const navigate = useNavigate();

    const handleDelete = (index) => {
        const newCartData = cartData.filter((item, i) => i !== index);
        setCartData(newCartData);
        console.log('Item deleted at index:', index);
    };

    const handleProceedToPayment = () => {
        console.log('Proceeding to payment with items:', cartData);
        navigate('/paymentinfo');
    };

    const handleIncrementQuantity = () => {
        console.log('Incremented button pressed:');
    };
    const handleDecrementQuantity = () => {
        console.log('Decrement button pressed:');
    };

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <ShoppingCartCard
                    data={cartData}
                    handleDelete={handleDelete}
                    handleProceedToPayment={handleProceedToPayment}
                    handleIncrementQuantity={handleIncrementQuantity}
                    handleDecrementQuantity={handleDecrementQuantity}
                />
            </Background>
        </Box>
    );
}
