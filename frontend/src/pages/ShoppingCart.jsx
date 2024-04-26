import React, {useEffect, useState} from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import ShoppingCartCard from '../components/ShoppingCartCard.jsx';
import { useNavigate } from 'react-router-dom';

const HOST = import.meta.env.VITE_HOST;


export default function ShoppingCart() {
    const [cartData, setCartData] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        async function fetchShoppingCart() {
            try {
                const response = await fetch(`http://${HOST}:8080/fabFlix/shoppingCart`,{
                    credentials: 'include'
                });
                if (response.status !== 200) {
                    console.error('Response is not status 200');
                    return;
                }

                const data = await response.json();
                let movieArr = [];

                for (const movieTitle in data) {
                    const movieInfo = data[movieTitle];

                    const tempMovieObj = {
                        title : movieTitle,
                        quantity : movieInfo.quantity,
                        price: movieInfo.price,
                        totalPrice: movieInfo.totalPrice,
                        id : movieInfo.id
                    };

                    console.log("Movie Title:", tempMovieObj.title);
                    console.log("Quantity:", tempMovieObj.quantity);
                    console.log("Total Price:", tempMovieObj.totalPrice);
                    console.log("Price:", tempMovieObj.price);
                    console.log("ID:", tempMovieObj.id);
                    console.log("----------");
                    movieArr.push(tempMovieObj)
                }


                if (mounted) {
                    console.log("movie arr: " + movieArr);
                    setCartData(movieArr);
                }
            } catch (error) {
                console.error('Error fetching data: ', error);
            }
        }

        fetchShoppingCart();
        return () => {
            mounted = false;
        }
    }, []);

    const handleDelete = (index) => {
        const newCartData = cartData.filter((item, i) => i !== index);
        setCartData(newCartData);
        console.log('Item deleted at index:', index);
    };

    const handleProceedToPayment = () => {
        console.log('Proceeding to payment with items:', cartData);
        navigate('/paymentinfo');
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
                />
            </Background>
        </Box>
    );
}
