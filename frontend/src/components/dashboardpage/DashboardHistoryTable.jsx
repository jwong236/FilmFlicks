import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Box, Typography } from '@mui/material';

const DashboardHistoryTable = ({ history }) => {
    return (
        <Box sx={{padding: '0rem 1rem 1rem 0rem'}}>
            <Typography variant="h6" gutterBottom component="div" sx={{ color: 'primary.dark' }}>
                History
            </Typography>
            <TableContainer component={Paper} sx={{overflow: 'auto' }}>
                <Table stickyHeader aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ width: '7%' }}>Status</TableCell>
                            <TableCell sx={{ width: '13%' }}>Action</TableCell>
                            <TableCell sx={{ width: '55%' }}>Message</TableCell>
                            <TableCell sx={{ width: '25%' }}>Data</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {history.map((row, index) => (
                            <TableRow key={index}>
                                <TableCell>{row.status}</TableCell>
                                <TableCell>{row.action}</TableCell>
                                <TableCell>
                                    <Box sx={{ wordBreak: 'break-word'}}>
                                        {row.message}
                                    </Box>
                                </TableCell>
                                <TableCell>
                                        {JSON.stringify(row.data, null, 2)}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default DashboardHistoryTable;
