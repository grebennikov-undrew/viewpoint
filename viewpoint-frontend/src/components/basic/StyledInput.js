
import React, { useState } from 'react';
import { Button as MuiButton } from '@mui/material';

export const Button = (props) => {
    const {title} = props;

    return (
        <MuiButton {...props}>
            {title}
        </MuiButton>
    )
}
