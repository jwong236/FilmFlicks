import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const URL = import.meta.env.VITE_BACKEND_URL;

export const useShoppingCartPageHooks = () => {
    const [cartData, setCartData] = useState([]);
    const [total, setTotal] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        let mounted = true;

        async function fetchShoppingCart() {
            try {
                const response = await fetch(`${URL}/transaction/shopping-cart`, {
                    credentials: 'include',
                });

                if (response.status === 401) {
                    navigate('/login');
                } else {
                    const data = await response.json();
                    const formattedCartData = Object.entries(data.cartItems).map(([title, item]) => ({
                        title,
                        quantity: item.quantity,
                        price: item.price,
                        totalPrice: item.totalPrice,
                    }));

                    if (mounted) {
                        setCartData(formattedCartData);
                        setTotal(data.totalPrice);
                    }
                }
            } catch (error) {
                console.error('Error fetching shopping cart:', error);
            }
        }

        fetchShoppingCart();

        return () => {
            mounted = false;
        };
    }, [navigate]);

    const incrementItem = async (movie) => {
        try {
            const randomPrice = 10 // Arbitrary price for now


            // Use the random price in the fetch URL
            const response = await fetch(
                `${URL}/transaction/shopping-cart/add?title=${encodeURIComponent(movie.title)}&price=${randomPrice}&quantity=1`,
                {
                    method: 'POST',
                    credentials: 'include',
                }
            );

            if (response.status === 401) {
                navigate('/login');
            } else if (response.ok) {
                setCartData((prev) =>
                    prev.map((item) =>
                        item.title === movie.title
                            ? { ...item, quantity: item.quantity + 1, totalPrice: (item.quantity + 1) * randomPrice }
                            : item
                    )
                );
                setTotal((prev) => prev + randomPrice);
            }
        } catch (error) {
            console.error('Error incrementing cart quantity:', error);
        }
    };


    const decrementItem = async (movie) => {
        try {
            if (movie.quantity === 1) {
                await deleteItem(movie);
            } else {
                const response = await fetch(`${URL}/transaction/shopping-cart/remove?title=${encodeURIComponent(movie.title)}&quantity=1`, {
                    method: 'DELETE',
                    credentials: 'include',
                });

                if (response.status === 401) {
                    navigate('/login');
                } else if (response.ok) {
                    setCartData((prev) =>
                        prev.map((item) =>
                            item.title === movie.title
                                ? { ...item, quantity: item.quantity - 1, totalPrice: (item.quantity - 1) * item.price }
                                : item
                        )
                    );
                    setTotal((prev) => prev - movie.price);
                }
            }
        } catch (error) {
            console.error('Error decrementing cart quantity:', error);
        }
    };

    const deleteItem = async (movie) => {
        try {
            const response = await fetch(`${URL}/transaction/shopping-cart/remove?title=${encodeURIComponent(movie.title)}&quantity=${movie.quantity}`, {
                method: 'DELETE',
                credentials: 'include',
            });

            if (response.status === 401) {
                navigate('/login');
            } else if (response.ok) {
                setCartData((prev) => prev.filter((item) => item.title !== movie.title));
                setTotal((prev) => prev - movie.totalPrice);
            }
        } catch (error) {
            console.error('Error deleting item from cart:', error);
        }
    };

    const handleProceedToPayment = () => {
        navigate('/paymentinfo', { state: { cartData, total } });
    };

    return {
        cartData,
        total,
        incrementItem,
        decrementItem,
        deleteItem,
        handleProceedToPayment,
    };
};
