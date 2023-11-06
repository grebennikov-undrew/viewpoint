import * as React from 'react';
import Stack from '@mui/material/Stack';
import Chip from '@mui/material/Chip';
import Autocomplete from '@mui/material/Autocomplete';
import TextField from '@mui/material/TextField';

export default function SelectTags({options, values, onSelectChange}) {

    // if (!options) return;
    return (
        <Autocomplete
            multiple
            id="size-small-outlined-multi"
            // size="small"
            options={optionsa}
            value={values}
            getOptionLabel={(option) => option}
            onChange={onSelectChange}
            renderInput={(params) => (
            <TextField {...params} size="medium" variant="standard" label="Columns" placeholder="  add..." />
            )}
        />
    );
}

// Top 100 films as rated by IMDb users. http://www.imdb.com/chart/top
const optionsa = [
  "col1", "col2", "col3"
];