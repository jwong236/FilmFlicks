import React, {useEffect, useState} from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import ShoppingCartCard from '../components/ShoppingCartCard.jsx';
import { useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_URL;


export default function ShoppingCart() {
    const [cartData, setCartData] = useState([]);
    const [total, setTotal] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;
        async function fetchShoppingCart() {
            try {
                const response = await fetch(`${URL}/shoppingCart`,{
                    credentials: 'include'
                });

                if (response.status === 401) {
                    console.log("have to login to access shopping cart");
                    navigate('/login');
                }else{
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
            const response = await fetch(`${URL}/totalPrice`,{
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
            const response = await fetch(`${URL}/delete`,{
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
        navigate('/paymentinfo', { state: { cartData } });
    };


    async function addToShoppingCart(movie){
        try {
            console.log("the movie that was added is : " + movie.title);
            const response = await fetch(`${URL}/add`,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ "movieTitle": movie.title}),
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
                // const jsonData = await response.json();
                // console.log("no need to login");
                // console.log("response from add: ", jsonData);
                console.log("succesfully incremented movie");
            }
        } catch (error) {
            console.error('Error adding into cart: ', error);
        }
    }


    const handleIncrementQuantity = async (movie, index) => {
        console.log('Incremented button pressed:');
        try {
            // const newCartData = cartData.((item, i) => i !== index);
            const newCartData = cartData.map((item, i) => {
                if (i === index) {
                    return { ...item, quantity: item.quantity + 1,  totalPrice: (item.totalPrice + item.price)}; // Create a new object with updated quantity
                }
                return item; // Keep other items unchanged
            });

            await addToShoppingCart(movie);
            await totalPrice();
            setCartData(newCartData);
            console.log('Item added at index:', index);
        } catch (error) {
            console.error('Error handling incrementing: ', error);
        }
    };
    // const handleDecrementQuantity = () => {
    //     console.log('Decrement button pressed:');
    // };

    async function subtractFromCart(movie){
        try {
            const response = await fetch(`${URL}/subtract`,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({"movieTitle": movie.title}),
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
                    //console.log("response from add: ", jsonData);
                    // shoppingCart();
                    // totalPrice();
                    console.log("successfully decremented movie from cart");
                }
            }
        } catch (error) {
            console.error('Error decreasing from cart: ', error);
        }
    }

    const handleDecrementQuantity = async (movie, index) => {
        console.log('Decrement button pressed:');
        try {
            // const newCartData = cartData.filter((item, i) => i !== index);
            const newCartData = cartData.map((item, i) => {
                if (i === index) {
                    return { ...item, quantity: item.quantity - 1, totalPrice: (item.totalPrice - item.price)};
                }
                return item;
            });
            await subtractFromCart(movie);
            await totalPrice();
            setCartData(newCartData);
            console.log('Item added at index:', index);
        } catch (error) {
            console.error('Error handling decrementing: ', error);
        }
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
