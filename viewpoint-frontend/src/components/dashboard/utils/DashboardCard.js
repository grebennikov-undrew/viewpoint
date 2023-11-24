import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';

const DashboardCard = (props) => {
    const { id, name, description, picture } = props
    const { handleOpenClick, handleEditClick, handleDeleteClick } = props
    return (
    <Card >
      <CardActionArea>
        {/* <CardMedia
          component="img"
          height="140"
          image="/static/images/cards/contemplative-reptile.jpg"
          alt="green iguana"
        /> */}
        <CardContent onClick={(e) => handleOpenClick(e,id)}>
          <Typography gutterBottom variant="h3" component="div">
            {name}
          </Typography>
          <Typography variant="body2" color="text.secondary">
            {description}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions>
        <Button size="small" color="primary" onClick={(e) => handleOpenClick(e,id)}>
          Open
        </Button>
        <Button size="small" color="primary" onClick={(e) => handleEditClick(e,id)}>
          Edit
        </Button>
        <Button size="small" color="error" style={{marginLeft: "auto"}} onClick={(e) => handleDeleteClick(e,id)}>
          Delete
        </Button>
      </CardActions>
    </Card>
    )
}

export default DashboardCard;