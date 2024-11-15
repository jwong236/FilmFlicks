import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, useTheme } from '@mui/material';

export default function ConfirmationCardTable({ cartData, saleData }) {
    const theme = useTheme();

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        {['Sale ID', 'Title', 'Quantity', 'Price', 'Total'].map((header) => (
                            <TableCell
                                key={header}
                                sx={{
                                    fontWeight: 'bold',
                                    backgroundColor: theme.palette.secondary.main,
                                    color: theme.palette.primary.contrastText,
                                }}
                                align={header === 'Title' ? 'left' : 'right'}
                            >
                                {header}
                            </TableCell>
                        ))}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {cartData.map((item, index) => (
                        <TableRow key={index}>
                            <TableCell align="right">{saleData[index]?.id || 'N/A'}</TableCell>
                            <TableCell>{item.title}</TableCell>
                            <TableCell align="right">{item.quantity}</TableCell>
                            <TableCell align="right">{`$${item.price.toFixed(2)}`}</TableCell>
                            <TableCell align="right">{`$${item.totalPrice.toFixed(2)}`}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
