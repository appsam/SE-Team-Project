import React from 'react';

const StarRating = ({ rating }) => {
  const totalStars = 5;
  const filledStars = Math.round(rating);
  const starsArray = Array.from({ length: totalStars }, (_, index) => index < filledStars);

  return (
    <div className="rating">
      {starsArray.map((isFilled, index) => (
        <input
          key={index}
          type="radio"
          name="rating-2"
          className={`mask mask-star-2 ${isFilled ? 'bg-orange-400' : ''}`}
          checked={isFilled}
          readOnly
        />
      ))}
    </div>
  );
};

export default StarRating;