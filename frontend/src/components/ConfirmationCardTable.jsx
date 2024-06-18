import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, useTheme } from '@mui/material';

export default function ConfirmationCardTable({ data }) {
    const theme = useTheme(); // Access theme to use primary color

    const headerStyle = {
        fontWeight: 'bold',
        backgroundColor: 'secondary.main',
        color: 'primary.main'
    };

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell sx={headerStyle}>Sale ID</TableCell>
                        <TableCell sx={headerStyle}>Title</TableCell>
                        <TableCell sx={headerStyle} align="right">Quantity</TableCell>
                        <TableCell sx={headerStyle} align="right">Price</TableCell>
                        <TableCell sx={headerStyle} align="right">Total</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {data.map((item, index) => (
                        <TableRow key={index}>
                            <TableCell component="th" scope="row">{item.saleId}</TableCell>
                            <TableCell>{item.title}</TableCell>
                            <TableCell align="right">{item.quantity}</TableCell>
                            <TableCell align="right">{`$${item.price.toFixed(2)}`}</TableCell>
                            <TableCell align="right">{`$${item.total.toFixed(2)}`}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
