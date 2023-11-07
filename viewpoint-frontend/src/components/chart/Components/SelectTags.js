import React, { useState, useEffect } from 'react';
import Stack from '@mui/material/Stack';
import Chip from '@mui/material/Chip';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';

export default function SelectTags({options, values, label, onSelectChange}) {

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
                <TextField {...params} size="medium" variant="standard" label={label} placeholder="  add..." />
            )}
        />
    );
}
