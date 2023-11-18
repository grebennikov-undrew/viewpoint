import React, { useState, useEffect } from 'react';
import Stack from '@mui/material/Stack';
import Chip from '@mui/material/Chip';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';

export default function SelectTags(props) {

    const {options, values, label, onSelectChange, placeholder, variant, size} = props;

    // if (!options) return;
    return (
        <Autocomplete
            multiple
            id="size-small-outlined-multi"
            // size="small"
            options={options}
            value={values}
            getOptionLabel={(option) => option}
            onChange={onSelectChange}
            renderInput={(params) => (
                <TextField {...params} size={size || "medium"} variant={variant || "standard"} label={label} placeholder={placeholder || "add.."} />
            )}
        />
    );
}
