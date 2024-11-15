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

                    const formattedCartData = Object.entries(data.cartItems).map(([id, item]) => ({
                        id,
                        title: item.title,
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
            const response = await fetch(
                `${URL}/transaction/shopping-cart/add?id=${encodeURIComponent(movie.id)}&title=${encodeURIComponent(
                    movie.title
                )}&price=${movie.price}&quantity=1`,
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
                        item.id === movie.id
                            ? {
                                ...item,
                                quantity: item.quantity + 1,
                                totalPrice: (item.quantity + 1) * item.price,
                            }
                            : item
                    )
                );
                setTotal((prev) => prev + movie.price);
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
                const response = await fetch(
                    `${URL}/transaction/shopping-cart/remove?id=${encodeURIComponent(movie.id)}&quantity=1`,
                    {
                        method: 'DELETE',
                        credentials: 'include',
                    }
                );

                if (response.status === 401) {
                    navigate('/login');
                } else if (response.ok) {
                    setCartData((prev) =>
                        prev.map((item) =>
                            item.id === movie.id
                                ? {
                                    ...item,
                                    quantity: item.quantity - 1,
                                    totalPrice: (item.quantity - 1) * item.price,
                                }
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
            const response = await fetch(
                `${URL}/transaction/shopping-cart/remove?id=${encodeURIComponent(movie.id)}&quantity=${movie.quantity}`,
                {
                    method: 'DELETE',
                    credentials: 'include',
                }
            );

            if (response.status === 401) {
                navigate('/login');
            } else if (response.ok) {
                setCartData((prev) => prev.filter((item) => item.id !== movie.id));
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
