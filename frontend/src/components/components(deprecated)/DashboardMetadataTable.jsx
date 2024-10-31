import React, { useState, useEffect } from 'react';
import { Typography, Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const URL = import.meta.env.VITE_URL;

export default function DashboardMetadataTable() {
    const [metadata, setMetadata] = useState([]);

    useEffect(() => {
        const fetchMetadata = async () => {
            try {
                const response = await fetch(`${URL}/metadata`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    credentials: 'include'
                });
                const data = await response.json();
                setMetadata(data);
            } catch (error) {
                console.error("Failed to fetch metadata:", error);
            }
        };
        fetchMetadata();
    }, []);

    return (
        <Box>
            <Typography variant="h6" gutterBottom component="div" sx={{color: 'primary.dark'}}>
                Database Metadata
            </Typography>
            <TableContainer component={Paper}>
                <Table aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Table Name</TableCell>
                            <TableCell align="right">Columns</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {metadata.map((table) => (
                            <TableRow
                                key={table.tableName}
                                sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                            >
                                <TableCell component="th" scope="row">
                                    {table.tableName}
                                </TableCell>
                                <TableCell align="right">
                                    {table.columns.map(col => `${col.columnName} (${col.dataType})`).join(', ')}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
}
