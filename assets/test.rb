require 'nokogiri'
require 'open-uri'
require "active_support/core_ext"
require 'open-uri'

restaurants = []

  restaurant_page = Nokogiri::HTML(open("http://localhost/sk.html")).css('.menu_list').each do |rest|
      category_name = rest.css('.menu_btn').text.strip
      category_meal = rest.css('.menu_togle li').each do |meal|
        #puts meal
        meal_name = meal.css('.about_box .title a').text.strip
        meal_text = meal.css('.about_box .text').text.strip
        meal_price= meal.css('.price_box .price').text
        #meal_currency = meal_price.sub(

        abort meal_price
      end
      #puts category_meal


  end
 


