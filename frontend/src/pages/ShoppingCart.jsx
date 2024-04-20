import React, { useEffect, useState } from 'react';
import { Box, Container } from "@mui/material";

const HOST = import.meta.env.VITE_HOST;

export default function MovieList() {
    // const [titles, setTitles] = useState([]);
    //
    // useEffect(() => {
    //     async function fetchMovieTitles() {
    //         try {
    //             const response = await fetch(`http://${HOST}:8080/fabFlix/add`); // Replace shoppingcart with confirmation when ready
    //             if (!response.ok) {
    //                 console.error('Response is not status 200');
    //                 return;
    //             }
    //             const data = await response.json();
    //             setTitles(data.map(movie => movie.title));
    //         } catch (error) {
    //             console.error('Error fetching movie titles: ', error);
    //         }
    //     }
    //
    //     fetchMovieTitles();
    // }, []);
    //
    async function addToCart(){
        try {
            const response = await fetch(`http://${HOST}:8080/fabFlix/add`,{
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ "movieTitle": "Bigfoot"}),
                credentials: 'include'
            }); // Replace shoppingcart with confirmation when ready
            if (!response.ok) {
                console.error('response is not status 200');
            }

            console.log("DATA AS TEXT IN MOVIE LIST " + response.text);

            if (response.status === 401){
                console.log("REDIRECTION FROM MOVIE LIST");
                navigate('/login')
            }else{
                const jsonData = await response.json();
                console.log("no need to login");
                console.log("response from add: ", jsonData);

            }
        } catch (error) {
            console.error('Error adding into cart: ', error);
        }
    }
    return (
        <Container>
            {/*<Box sx={{*/}
            {/*    display: 'flex',*/}
            {/*    flexDirection: 'column',*/}
            {/*    justifyContent: 'center',*/}
            {/*    alignItems: 'center',*/}
            {/*    width: '100%',*/}
            {/*    minHeight: '100vh',*/}
            {/*    backgroundColor: '#f1f1f1',*/}
            {/*    padding: 3*/}
            {/*}}>*/}
            {/*    {titles.length > 0 ? (*/}
            {/*        <Box sx={{ width: '60%', backgroundColor: 'white', padding: 2, borderRadius: '8px', boxShadow: '0 2px 4px rgba(0,0,0,0.1)' }}>*/}
            {/*            <h2>Template Webpage</h2>*/}
            {/*            <ul>*/}
            {/*                {titles.map((title, index) => (*/}
            {/*                    <li key={index}>{title}</li>*/}
            {/*                ))}*/}
            {/*            </ul>*/}
            {/*        </Box>*/}
            {/*    ) : (*/}
            {/*        <p>No titles found</p>*/}
            {/*    )}*/}
            {/*</Box>*/}
            <button onClick={addToCart}>
                add
            </button>
        </Container>
    );
}
