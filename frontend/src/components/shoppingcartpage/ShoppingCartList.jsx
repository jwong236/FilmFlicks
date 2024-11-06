import React from 'react';
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

export default function ShoppingCartList({
                                             cartData,
                                             totalAmount,
                                             onDeleteItem,
                                             onProceedToPayment,
                                             onIncrementItem,
                                             onDecrementItem
                                         }) {
    const theme = useTheme();

    const headerStyle = {
        color: 'white',
        backgroundColor: theme.palette.info.main
    };

    const buttonStyle = {
        backgroundColor: 'primary.main',
        color: 'secondary.light',
        height: '1.5rem',
        '&:hover': {
            backgroundColor: 'primary.dark',
            color: 'secondary.light'
        }
    };

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD'
        }).format(amount);
    };

    return (
        <Box sx={{ width: '100%' }}>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            {['Title', 'Quantity', 'Price', 'Total', 'Delete'].map((header) => (
                                <TableCell key={header} sx={headerStyle} align={header === 'Title' ? 'left' : 'right'}>
                                    {header}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody sx={{ backgroundColor: theme.palette.secondary.light }}>
                        {cartData.map((movie, index) => (
                            <TableRow key={index}>
                                <TableCell>{movie.title}</TableCell>
                                <TableCell align="right" sx={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                                    <Button sx={buttonStyle} onClick={() => onIncrementItem(movie)}>+</Button>
                                    {movie.quantity}
                                    <Button sx={buttonStyle} onClick={() => onDecrementItem(movie)}>-</Button>
                                </TableCell>
                                <TableCell align="right">{formatCurrency(movie.price)}</TableCell>
                                <TableCell align="right">{formatCurrency(movie.totalPrice)}</TableCell>
                                <TableCell align="right">
                                    <Button sx={buttonStyle} onClick={() => onDeleteItem(movie)}>Delete</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            {totalAmount !== undefined && (
                <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-end', p: 2 }}>
                    <Typography variant="h6" sx={{ marginRight: 2, color: 'primary.main', textDecoration: 'underline' }}>
                        Total: {formatCurrency(totalAmount)}
                    </Typography>
                    <Button variant="contained" sx={{ textTransform: 'none' }} onClick={onProceedToPayment}>
                        Proceed to Payment
                    </Button>
                </Box>
            )}
        </Box>
    );
}
