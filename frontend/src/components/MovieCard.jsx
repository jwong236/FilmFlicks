import {Box, Card, CardHeader, CardContent, Chip, Typography, Link as MuiLink} from '@mui/material'
import { Link as RouterLink } from "react-router-dom";
export default function MovieCard(props) { // title, year, director, genres, stars, rating
    const bull = (
        <Box
            component="span"
            sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
        >
            •
        </Box>
    );

    return (
        <Card sx={{ width: '100%', height: '80vh', backgroundColor: '#F8E6AE', borderRadius: '1rem', display: 'flex', flexDirection: 'column',  justifyContent: 'flex-end'}}>
            <Box sx={{display: "flex", flexDirection: "column", height: '50%', justifyContent: 'space-around'}}>
                <Box sx={{display: "flex", width: "100%",  justifyContent: 'space-between'}}>
                    <Box sx={{display: "flex", flexDirection: "column",  alignItems: "flex-start", paddingLeft: '1rem'}}>
                        <CardHeader
                            title={
                                <MuiLink component={RouterLink} to={`/singlemovie?title=${encodeURIComponent(props.title)}`} style={{ textDecoration: 'none', color: '#803D33' }}>
                                    {props.title}
                                </MuiLink>
                            }
                            sx={{ padding: 0 }}
                        />
                        <CardContent sx={{padding: 0}}>
                            <Typography sx={{color: '#803D33'}}>
                                {props.year}{bull}{props.director}
                            </Typography>

                        </CardContent>
                    </Box>
                    <Box sx={{ color: '#803D33', height: '100%', aspectRatio: 1, borderRadius: '5rem', border: '0.25rem solid #803D33', display: 'flex', alignItems: 'center', justifyContent: 'center', marginRight: '1rem'}}>{props.rating}</Box>
                </Box>
                <Box sx={{display: "flex", width: "100%", justifyContent: 'space-around'}}>
                    {props.stars.map((star, index) => (
                        <Typography key={index}>
                            <MuiLink component={RouterLink} to={`/singlestar?name=${encodeURIComponent(star)}`} style={{ textDecoration: 'none', color: '#803D33' }}>
                                {star}
                            </MuiLink>
                            {index < props.stars.length}
                        </Typography>
                    ))}
                </Box>

                <Box sx={{ display: "flex", width: "100%", paddingLeft: '1rem', flexWrap: "wrap" }}>
                    {props.genres.map((genre, index) => (
                        <Chip
                            key={index}
                            label={`#${genre}`}
                            sx={{ color: "#803D33", backgroundColor: '#E5BEB8', marginRight: '8px', marginBottom: '8px' }}
                        />
                    ))}
                </Box>
            </Box>

        </Card>
    )
}