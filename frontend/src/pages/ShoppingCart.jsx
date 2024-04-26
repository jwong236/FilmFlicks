import React, {useEffect, useState} from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import ShoppingCartCard from '../components/ShoppingCartCard.jsx';
import { useNavigate } from 'react-router-dom';

const HOST = import.meta.env.VITE_HOST;


export default function ShoppingCart() {
    const [cartData, setCartData] = useState([]);
    const [total, setTotal] = useState(0);
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
                    await totalPrice();
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

    async function totalPrice() {
        try {
            console.log("attempting to get total price");
            const response = await fetch(`http://${HOST}:8080/fabFlix/totalPrice`,{
                credentials: 'include'
            });
            if (!response.ok) {
                console.error('Response is not status 200');
                return;
            }
            const data = await response.json();
            console.log("TOTAL : " + data.total);
            setTotal(data.total);
        } catch (error) {
            console.error('Error Calculating Total Price: ', error);
        }
    }
    async function deleteFromCart(movie){
        try {
            console.log("this movie is being deleted : " + movie);
            const response = await fetch(`http://${HOST}:8080/fabFlix/delete`,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    title: movie.title,
                    quantity: movie.quantity,
                    price: movie.price,
                    totalPrice: movie.totalPrice,
                    id: movie.id
                }),
                credentials: 'include'
            }); // Replace totalPrice with confirmation when ready
            if (!response.ok) {
                console.error('response is not status 200');
            }

            console.log("DATA AS TEXT IN MOVIE LIST " + response.text);

            if (response.status === 401){
                console.log("REDIRECTION FROM MOVIE LIST");
                navigate('/login')
            }else{
                console.log("no need to login");
                if (response.status === 405){
                    console.log("cant decrement 1 or movie doesnt exist");
                }else if (response.status === 200){
                    // const jsonData = await response.json();
                    console.log("succesfully deleted from cart");
                }
            }
        } catch (error) {
            console.error('Error decreasing from cart: ', error);
        }
    }


    // const handleDelete = (movie,index) => {
    //     const newCartData = cartData.filter((item, i) => i !== index);
    //     deleteFromCart(movie);
    //     totalPrice();
    //     setCartData(newCartData);
    //     console.log('Item deleted at index:', index);
    // };

    const handleDelete = async (movie, index) => {
        try {
            const newCartData = cartData.filter((item, i) => i !== index);
            await deleteFromCart(movie);
            await totalPrice();
            setCartData(newCartData);
            console.log('Item deleted at index:', index);
        } catch (error) {
            console.error('Error handling deletion: ', error);
        }
    };

    const handleProceedToPayment = () => {
        console.log('Proceeding to payment with items:', cartData);
        navigate('/paymentinfo');
    };

<<<<<<< HEAD
    console.log("total " + total);
=======
    const handleIncrementQuantity = () => {
        console.log('Incremented button pressed:');
    };
    const handleDecrementQuantity = () => {
        console.log('Decrement button pressed:');
    };
>>>>>>> c424c04a0daf22ae05c0cf0464e9025d13b80ad7

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
                    total={total}
                    handleDelete={handleDelete}
                    handleProceedToPayment={handleProceedToPayment}
                    handleIncrementQuantity={handleIncrementQuantity}
                    handleDecrementQuantity={handleDecrementQuantity}
                />
            </Background>
        </Box>
    );
}
