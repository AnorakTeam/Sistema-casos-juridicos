import React from 'react';
import { useForm } from 'react-hook-form';

export function AreaFormTest() {
  const { register, handleSubmit, formState: { errors }, control } = useForm();
  const onSubmit = data => {console.log(data)};
  
  return (
    <div>
        <form onSubmit={handleSubmit(onSubmit)}>
        
        <label>Nombre del area</label>
        <input type="text" placeholder="area" {...register("area", {required: true})} />

        <input type="submit" />
        </form>
    </div>
  );
}