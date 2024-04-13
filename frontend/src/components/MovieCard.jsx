import {Box, Card, CardHeader, CardContent, Chip, Typography} from '@mui/material'
export default function MovieCard(props) {
    const bull = (
        <Box
            component="span"
            sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}
        >
            •
        </Box>
    );

    return (
        <Card sx={{ width: '50vw', height: '80vh', backgroundColor: '#F8E6AE', borderRadius: '1rem', display: 'flex', flexDirection: 'column',  justifyContent: 'flex-end'}}>
            <Box sx={{display: "flex", flexDirection: "column", height: '50%', justifyContent: 'space-around'}}>
                <Box sx={{display: "flex", width: "100%",  justifyContent: 'space-between'}}>
                    <Box sx={{display: "flex", flexDirection: "column",  alignItems: "flex-start", paddingLeft: '1rem'}}>
                        <CardHeader title={props.title} sx={{padding: 0, color: '#803D33'}}/>
                        <CardContent sx={{padding: 0}}>
                            <Typography sx={{color: '#803D33'}}>
                                {props.year}{bull}{props.director}
                            </Typography>

                        </CardContent>
                    </Box>
                    <Box sx={{ color: '#803D33', height: '100%', aspectRatio: 1, borderRadius: '5rem', border: '0.25rem solid #803D33', display: 'flex', alignItems: 'center', justifyContent: 'center', marginRight: '1rem'}}>{props.rating}</Box>
                </Box>
                <Box sx={{display: "flex", width: "100%",  justifyContent: 'space-around'}}>
                    <Typography>
                        {props.stars[0]}
                    </Typography>
                    <Typography>
                        {props.stars[1]}
                    </Typography>
                    <Typography>
                        {props.stars[2]}
                    </Typography>
                </Box>

                <Box sx={{display: "flex", width: "100%", paddingLeft: '1rem'}}>
                    <Chip label={props.genres} sx={{color: "#803D33", backgroundColor: '#E5BEB8'}}/>
                </Box>
            </Box>

        </Card>
    )
}