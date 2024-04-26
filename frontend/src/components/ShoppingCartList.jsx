import React, {useEffect, useState} from 'react';

import {
    Box,
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Typography,
    useTheme
} from '@mui/material';

const HOST = import.meta.env.VITE_HOST;

export default function ShoppingCartList({ data, total, handleDelete, handleProceedToPayment}) {
    const theme = useTheme();
    // async function totalPrice() {
    //     try {
    //         console.log("attempting to get total price");
    //         const response = await fetch(`http://${HOST}:8080/fabFlix/totalPrice`,{
    //             credentials: 'include'
    //         });
    //         if (!response.ok) {
    //             console.error('Response is not status 200');
    //             return;
    //         }
    //         const data = await response.json();
    //         console.log("TOTAL : " + data.total);
    //         total = data.total;
    //     } catch (error) {
    //         console.error('Error Calculating Total Price: ', error);
    //     }
    // }

    useEffect(() => {
        let mounted = true;


        // totalPrice();
        console.log(`total : ${total}`);
        return () => {
            mounted = false;
        }
    }, []);


    const headerStyle = {
        color: 'white',
        backgroundColor: theme.palette.info.main
    };

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    };

    // const totalSum = data.reduce((sum, item) => sum + item.total, 0);

    return (
        <Box sx={{ width: '100%' }}>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell sx={headerStyle}>Title</TableCell>
                            <TableCell sx={headerStyle} align="right">Quantity</TableCell>
                            <TableCell sx={headerStyle} align="right">Price</TableCell>
                            <TableCell sx={headerStyle} align="right">Total</TableCell>
                            <TableCell sx={headerStyle} align="right">Delete</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody sx={{ backgroundColor: theme.palette.secondary.light }}>
                        {data.map((movie, index) => (
                            <TableRow key={index}>
                                <TableCell component="th" scope="row">{movie.title}</TableCell>
                                <TableCell align="right">{movie.quantity}</TableCell>
                                <TableCell align="right">{formatCurrency(movie.price)}</TableCell>
                                <TableCell align="right">{formatCurrency(movie.totalPrice)}</TableCell>
                                <TableCell align="right">
                                    <Button
                                        sx={{
                                            backgroundColor: 'primary.main',
                                            color: 'secondary.light',
                                            '&:hover': {
                                                backgroundColor: 'primary.dark',
                                                color: 'secondary.light'
                                            }
                                        }}
                                        onClick={() => {
                                            handleDelete(movie, index);
                                        }}>
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', p: 2 }}>
                <Typography variant="h6" sx={{ marginRight: 2, color: 'primary.main', textDecoration: 'underline'}}>Total: {formatCurrency(total)}</Typography>
                <Button
                    variant="contained"
                    sx={{ textTransform: 'none' }}
                    onClick={handleProceedToPayment}
                >
                    Proceed to Payment
                </Button>
            </Box>
        </Box>
    );
}
