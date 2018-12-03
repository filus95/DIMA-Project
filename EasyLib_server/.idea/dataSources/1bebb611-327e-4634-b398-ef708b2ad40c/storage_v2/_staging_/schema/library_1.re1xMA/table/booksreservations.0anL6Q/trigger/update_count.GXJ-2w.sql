create trigger update_count_on_reservation
  after INSERT
  on booksreservations
  for each row
  BEGIN 
    update library_1.books set quantity = quantity - NEW.quantity
    where library_1.books.identifier = NEW.book_identifier;
  end;

