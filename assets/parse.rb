require 'nokogiri'
require 'open-uri'
require "active_support/core_ext"
require 'open-uri'

restaurants = []

doc = Nokogiri::HTML(open('http://hrum.com.ua/restaurants/all/kiev')).css('.item_box').each do |item|
  
  title = item.css('.title').text.strip
  key = item.css('.hd_inner a.restaurant_title')[0]['href'].sub('/menu/','')
  logo_src = item.css('.item_logo img')[0]['src']
  logo_alt = item.css('.item_logo img')[0]['alt']
  
  geo_lng = item.css('.address .gmaper')[0]['data-lng']
  geo_lat = item.css('.address .gmaper')[0]['data-lat']
  geo_title=item.css('.address .gmaper')[0]['data-title']

  note = item.css('.note').text.strip
  
  min_cost      = item.css('.info_box li')[1].css('span').text
  delivery_time = item.css('.info_box li')[5].css('span').text.sub(/[^\d]+/, '')
  delivery      = item.css('.info_box li')[3].css('span').text.split
  category      = item.css('.info_box li')[2].css('span').text

  image_path = "../public/media/logo/" + logo_src.sub('/images/', '') 
  open(image_path, 'wb') do |file|
    file << open("http://hrum.com.ua#{logo_src}").read
  end

  r = {
      name: key,
      title: title,
      lat: geo_lat,
      lng: geo_lng,
      logo: {
        src: logo_src.sub('/images.','/public/media/logo'),
        alt: logo_alt
      },
      address: {
        street: geo_title
      },
      info: {
        note: note,
        category: category
      },
      delivery: {
        min_cost: min_cost.sub('грн.','').strip,
        time: delivery_time,
        cost: delivery[0],
        currency: delivery[1].sub('грн.','UAH').strip
      }
    
  }

  restaurants.push r
end


rests = restaurants.map(&:to_json).join(",\n")

rests = "[#{rests}]"

File.open('restaurants.json', 'w') { |file| file.write rests }