import React, {useEffect, useState} from 'react';
import { Box } from "@mui/material";
import Navbar from '../components/Navbar';
import Background from '../components/Background.jsx';
import PaymentInfoCard from '../components/PaymentInfoCard.jsx';
import {useNavigate} from "react-router-dom";


const HOST = import.meta.env.VITE_HOST;


export default function PaymentInfo() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [expirationDate, setExpirationDate] = useState('');
    const navigate = useNavigate();
    const [total, setTotal] = useState(0);

    const handlePlaceOrder = async () => {
        console.log("Order Details:");
        console.log("First Name:", firstName);
        console.log("Last Name:", lastName);
        console.log("Credit Card Number:", creditCardNumber);
        console.log("Expiration Date:", expirationDate);
        console.log("Total:", total);
        try {
            const response = await fetch(`http://${HOST}:8080/fabFlix/payment`,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    firstName: firstName,
                    lastName : lastName,
                    creditCardNumber: creditCardNumber,
                    expirationDate: expirationDate
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
                if (response.status === 406){
                    console.log("wrong credit card info");
                }else if (response.status === 200){
                    const jsonData = await response.json();
                    console.log("response from paying: ", jsonData);

                    // shoppingCart();
                    // totalPrice();
                    console.log("payment success");
                    navigate('/confirmation', { state: { orderDetails: jsonData} });
                }
            }
        } catch (error) {
            console.error('Error checking out cart: ', error);
        }

    };

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

    useEffect(() => {
        try{
            totalPrice()
        }catch(error){
            console.error('Error handling totalPrice: ', error);

        }
    }, []);

    return (
        <Box sx={{
            display: 'flex',
            height: '100vh',
            width: '100vw',
            flexDirection: 'column'
        }}>
            <Navbar />
            <Background sx={{ justifyContent: 'center', alignItems: 'center'}}>
                <PaymentInfoCard
                    firstName={firstName}
                    lastName={lastName}
                    creditCardNumber={creditCardNumber}
                    expirationDate={expirationDate}
                    setFirstName={setFirstName}
                    setLastName={setLastName}
                    setCreditCardNumber={setCreditCardNumber}
                    setExpirationDate={setExpirationDate}
                    total={total}
                    handlePlaceOrder={handlePlaceOrder}
                    sx={{ height: '80vh', width: '30vw' }}
                />
            </Background>
        </Box>
    );
}
