import React from 'react';
import { Input } from '@/components/ui/input';

export function FormInput({ name, label, type = 'text', register, errors, rules, ...props }) {
  return (
    <div className="flex flex-col gap-1.5 w-full">
      {label && (
        <label htmlFor={name} className="text-sm font-medium leading-none">
          {label}
        </label>
      )}

      <Input
        id={name}
        type={type}
        {...register(name, rules)}
        {...props}
        className={errors?.[name] ? 'border-red-500' : ''}
      />

      {errors?.[name] && (
        <p className="text-xs text-red-500">
          {errors[name]?.message}
        </p>
      )}
    </div>
  );
}